import io
import base64

search_image = "c:\\Users\\ASUS\\Desktop\\DoAN7\\SimilaritySearch\\FootwearDataset\\FootwearImg\\39944.jpg"
encoded = base64.b64encode(open(search_image, "rb").read())


image = base64.decodestring(encoded)




  