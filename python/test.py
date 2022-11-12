import doAn 
import cv2
import matplotlib.pyplot as plt
from skimage import data
from skimage import io
from skimage import transform
from skimage import color
import math
from PIL import Image
from numpy.linalg import norm
import numpy


import pickle
vectors = pickle.load(open("vectors.pkl","rb"))
paths = pickle.load(open("paths.pkl","rb"))



search_image = "c:\\Users\\ASUS\\Desktop\\DoAN7\\SimilaritySearch\\FootwearDataset\\FootwearImg\\39944.jpg"
# search_image = "c:\\Users\\ASUS\\Desktop\\DoAN7\\SimilaritySearch\\ImgTest\\22750.jpg"

# Trich dac trung anh search
# search_image = "https://admin.thegioigiay.com/upload/product/2022/10/634e61ea38dfb-18102022152058.jpg"
search_vector = doAn.extract_feature(search_image).flatten()
search_vector=search_vector/norm(search_vector)





from sklearn.neighbors import NearestNeighbors
neighbors =   NearestNeighbors(  n_neighbors = 16, algorithm = "brute", metric = "euclidean" )
neighbors.fit(vectors)
distance, indices = neighbors.kneighbors([search_vector])
print(indices)
print("dis")
print(distance)


# for file in indices[0][1:6]



K = 16
import matplotlib.pyplot as plt
axes = []
grid_size = int(math.sqrt(K))
fig = plt.figure(figsize=(10,5))
for id in range(K):
    axes.append(fig.add_subplot(grid_size, grid_size, id+1))
    axes[-1].set_title(distance[0][id])
    str =  paths[indices[0][id]]
    arr1 = str.split("\\")[-1];
    plt.imshow(Image.open("c:\\Users\\ASUS\\Desktop\DoAN7\\SimilaritySearch\\FootwearDataset\\FootwearImg\\" +arr1))

    # plt.imshow( "FootwearDataset/FootwearImg/" + arr1)
fig.tight_layout()
plt.show()

