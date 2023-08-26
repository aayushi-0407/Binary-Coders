from flask import Flask, request, jsonify
import numpy as np
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing.image import img_to_array, load_img
from tensorflow.keras.applications.resnet50 import preprocess_input

app = Flask(__name__)

# Load the pre-trained model
loaded_model = load_model('bug_bite_model.h5')

# List of class labels for reference
class_labels = ['Ant', 'Bed Bugs', 'Chigers', 'Fleas', 'Mosquitoes', 'Spiders', 'Ticks', 'No-Bites']

# Define a route for prediction
@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Get the image from the request
        image = request.files['image']
        
        # Load and preprocess the image
        img = load_img(image, target_size=(224, 224))
        img_array = img_to_array(img)
        img_array = preprocess_input(img_array)
        img_array = np.expand_dims(img_array, axis=0)
        
        # Make prediction using the loaded model
        predictions = loaded_model.predict(img_array)
        predicted_class = np.argmax(predictions)
        predicted_label = class_labels[predicted_class]
        
        response = {'predicted_label': predicted_label}
        
    except Exception as e:
        response = {'error': str(e)}
    
    return jsonify(response)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
