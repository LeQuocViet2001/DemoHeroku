from io import BytesIO
from skimage import color
from skimage import transform
from skimage import io
from skimage import data
from PIL import Image
from numpy.linalg import norm
import numpy
import base64
import pickle


url = "c:\\Users\\ASUS\\Desktop\\DoAN7\\SimilaritySearch\\FootwearDataset\\FootwearImg\\56994.jpg"
with open(url, "rb") as image_file:
    data = base64.b64encode(image_file.read())

# print(data)


def conv_(img, kernel):
    filter_size = kernel.shape[1]  # Chiều rộng hoặc dài của kernal
    result = numpy.zeros((img.shape))
    # Duyệt qua hình ảnh để nhân từ kernal với hình ảnh
    for r in numpy.uint16(numpy.arange(filter_size/2.0,
                          img.shape[0]-filter_size/2.0+1)):
        for c in numpy.uint16(numpy.arange(filter_size/2.0,
                                           img.shape[1]-filter_size/2.0+1)):

            curr_region = img[r-numpy.uint16(numpy.floor(filter_size/2.0)):r+numpy.uint16(numpy.ceil(filter_size/2.0)),
                              c-numpy.uint16(numpy.floor(filter_size/2.0)):c+numpy.uint16(numpy.ceil(filter_size/2.0))]
            # Nhân vùng vừa dữ liệu với kernel
            curr_result = curr_region * kernel
            conv_sum = numpy.sum(curr_result)  # Tinh tổng
            result[r, c] = conv_sum

    # Clipping the outliers of the result matrix.
    final_result = result[numpy.uint16(filter_size/2.0):  result.shape[0]-numpy.uint16(filter_size/2.0),
                          numpy.uint16(filter_size/2.0):  result.shape[1]-numpy.uint16(filter_size/2.0)]
    return final_result


def conv(img, kernel):

    # kiểm tra các điều kiện DIM
    # Check whether number of dimensions is the same
    if len(img.shape) != len(kernel.shape) - 1:
        print("Error: Number of dimensions in con v filter and image do not match.")
        exit()
    # Check if number of image channels matches the filter depth.
    if len(img.shape) > 2 or len(kernel.shape) > 3:
        if img.shape[-1] != kernel.shape[-1]:
            print("Error: Number of channels in both image and filter must match.")
            exit()
    # Check if filter dimensions are equal.
    if kernel.shape[1] != kernel.shape[2]:
        print(
            'Error: Filter must be a square matrix. I.e. number of rows and columns must match.')
        exit()
    if kernel.shape[1] % 2 == 0:  # Check if filter diemnsions are odd.
        print(
            'Error: Filter must have an odd size. I.e. number of rows and columns must be odd.')
        exit()

    # Tạo 1 ma trận rỗng để lấy kết quả trả về
    feature_maps = numpy.zeros((img.shape[0]-kernel.shape[1]+1,
                                img.shape[1]-kernel.shape[1]+1,
                                kernel.shape[0]))

    # Convolving the image by the filter(s).
    for filter_num in range(kernel.shape[0]):
        print("Filter ", filter_num + 1)
        # getting a filter from the bank.
        curr_filter = kernel[filter_num, :]
        """ 
            Checking if there are mutliple channels for the single filter.
            If so, then each channel will convolve the image.
            The result of all convolutions are summed to return a single feature map.
            """
        if len(curr_filter.shape) > 2:
            # Array holding the sum of all feature maps.
            conv_map = conv_(img[:, :, 0], curr_filter[:, :, 0])
            # Convolving each channel with the image and summing the results.
            for ch_num in range(1, curr_filter.shape[-1]):
                conv_map = conv_map + conv_(img[:, :, ch_num],
                                            curr_filter[:, :, ch_num])
        else:  # There is just a single channel in the filter.
            conv_map = conv_(img, curr_filter)
        # Holding feature map with the current filter.
        feature_maps[:, :, filter_num] = conv_map
    return feature_maps  # Returning all feature maps.


def pooling(feature_map, size=2, stride=2):
    # Tạo các ma trận 0 đón để nhận kết quả trả về
    # Sau kho Pooling thì kích cỡ của feature map sẽ bị giảm: Phụ thuộc vào sizePooling và stride
    pool_out = numpy.zeros((numpy.uint16((feature_map.shape[0]-size+1)/stride+1),
                            numpy.uint16(
                                (feature_map.shape[1]-size+1)/stride+1),
                            feature_map.shape[-1]))
    for map_num in range(feature_map.shape[-1]):
        r2 = 0
        for r in numpy.arange(0, feature_map.shape[0]-size+1, stride):
            c2 = 0
            for c in numpy.arange(0, feature_map.shape[1]-size+1, stride):
                # Láy vị trí max
                pool_out[r2, c2, map_num] = numpy.max(
                    [feature_map[r:r+size,  c:c+size, map_num]])
                c2 = c2 + 1
            r2 = r2 + 1
    return pool_out


def relu(feature_map):
    # Thực hiện ReLu: lấy những giá trị lớn hơn 0
    relu_out = numpy.zeros(feature_map.shape)
    # map_num chinh la số layer của 1 bức ảnh
    for map_num in range(feature_map.shape[-1]):
        for r in numpy.arange(0, feature_map.shape[0]):
            for c in numpy.arange(0, feature_map.shape[1]):
                relu_out[r, c, map_num] = numpy.max(
                    [feature_map[r, c, map_num], 0])  # = a ? a>0 : = 0
    return relu_out

############################################################################################


l1_filter = numpy.zeros((2, 3, 3))  # random cũng được mà để y cũng được
l1_filter[0, :, :] = numpy.array([[[-1, 0, 1],
                                  [-1, 0, 1],
                                  [-1, 0, 1]]])
l1_filter[1, :, :] = numpy.array([[[1,   1,  1],
                                  [0,   0,  0],
                                  [-1, -1, -1]]])

l2_filter = numpy.random.rand(3, 5, 5, 2)
l3_filter = numpy.random.rand(1, 7, 7, 3)


def ex_decode_img(image_string_encode):

    image = Image.open(BytesIO(base64.b64decode(image_string_encode)))
    image = image.resize((224, 224))
    image = image.convert('L')
    image = numpy.array(image)

    # image = color.rgb2gray(image)
    return image


def url_to_image(url):
    image = io.imread(url)
    image = transform.resize(image, (224, 224))
    image = color.rgb2gray(image)
    return image


def extract_feature(encode):

    img = ex_decode_img(encode)/255
    # First conv layer
    l1_feature_map = conv(img, l1_filter)
    l1_feature_map_relu = relu(l1_feature_map)
    l1_feature_map_relu_pool = pooling(l1_feature_map_relu, 2, 2)

    # Second conv layer
    l2_feature_map = conv(l1_feature_map_relu_pool, l2_filter)
    l2_feature_map_relu = relu(l2_feature_map)
    l2_feature_map_relu_pool = pooling(l2_feature_map_relu, 2, 2)

    # Third conv layer
    l3_feature_map = conv(l2_feature_map_relu_pool, l3_filter)
    l3_feature_map_relu = relu(l3_feature_map)
    l3_feature_map_relu_pool = pooling(l3_feature_map_relu, 2, 2)

    return l3_feature_map_relu_pool[:, :, 0]
##################################################


def searchImage(png_b64text):
    vectors = pickle.load(open(
        "C:\\Users\\ASUS\\Desktop\\Test\\test1\\src\\main\\java\\com\\example\\demo\\python\\vectors.pkl", "rb"))
    paths = pickle.load(open(
        "C:\\Users\\ASUS\\Desktop\\Test\\test1\\src\\main\\java\\com\\example\\demo\\python\\paths.pkl", "rb"))

    search_vector = extract_feature(png_b64text).flatten()
    search_vector = search_vector/norm(search_vector)

    from sklearn.neighbors import NearestNeighbors
    neighbors = NearestNeighbors(
        n_neighbors=16, algorithm="brute", metric="euclidean")
    neighbors.fit(vectors)
    distance, indices = neighbors.kneighbors([search_vector])
    print(indices)
    print("dis")
    print(distance)
    K = 16

    name = []
    for id in range(K):
        str = paths[indices[0][id]]
        # arr1 = str.split("\\")[-1]
        name.append(str)
    return name


# print(searchImage(data))



