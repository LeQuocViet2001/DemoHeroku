
import json
import numpy
import matplotlib.pyplot as mpl
import cv2
import numpy 
import urllib

class Divider:
    def __init__(self):{
        
    }
        
    def divide(self,numerator,denominator):
        return numerator/denominator
    
    
    def inArray(self):
        A = ["sasdasd.vosdkl lfjkv", "dofjsojf"]
        B = []
        return  json.dumps(A) 
    
    
    def num(self):
        l1_filter = numpy.zeros((2,3,3))
        A = l1_filter
        return  json.dumps(A) 
    
    def show(self):
        cv2.imshow( "", )
        
        
# METHOD #1: OpenCV, NumPy, and urllib
    def url_to_image(url):
	# download the image, convert it to a NumPy array, and then read
	# it into OpenCV format
        from PIL import Image
        import requests
        from io import BytesIO

        url = "https://m.media-amazon.com/images/I/41eD4DtAosL.jpg"
        response = requests.get(url)
        img = Image.open(BytesIO(response.content))
        return img
    
    # https://m.media-amazon.com/images/I/41eD4DtAosL.jpg

        

    

        
