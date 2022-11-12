from io import BytesIO
import base64

from numpy.linalg import norm
import matplotlib.pyplot as plt
from skimage import data

from PIL import Image


url = "c:\\Users\\ASUS\\Desktop\\DoAN7\\SimilaritySearch\\FootwearDataset\\FootwearImg\\39944.jpg"


with open(url, "rb") as image_file:
    data = base64.b64encode(image_file.read())
print(data)

im = Image.open(BytesIO(base64.b64decode(data)))
im.show()
