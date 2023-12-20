from flask import Flask, request, jsonify
import os
import pandas as pd
from sklearn.preprocessing import MultiLabelBinarizer
import tensorflow as tf
from collections import Counter

app = Flask(__name__)

# Load the model
try:
    model = tf.keras.models.load_model(r'D:\SEM7\bangkids\flaskapp\model_MBA.h5')
    print("Model loaded successfully.")
except Exception as e:
    print(f"Error loading model: {e}")

# Instantiate and fit the MultiLabelBinarizer
mlb = MultiLabelBinarizer()
mlb_classes = ['Cappuccino', 'Kopi Tubruk', 'Macchiato', 'Nasi Putih', 'Rawon', 'Soto Ayam', 
          'Fresh Orange Juice', 'Mineral Water', 'Roti Bakar', 'Extra Kerupuk Putih', 
          'Nasi Goreng Jawa', 'Latte', 'Bakmi Godog Keju', 'Teh Tubruk', 'Bakmi Goreng Jawa', 
          'Teh Tarik', 'Spaghetti Bolognese', 'Pisang Goreng', 'French Fries', 
          'Hot/Ice Lemon Tea', 'Espresso', 'Americano', 'Macchiato ', 'Double Espresso', 
          'Es Teh Susu (Gula Jawa)', 'Milo Dinosaur', 'Bakwan Goreng', 'Telur Orak Arik', 
          'Iced Tea', 'Pisang Goreng Tuna Sandwich', 'Jasmine', 'Hot Chocolate', 'Green Tea', 
          'Iced Peach Tea', 'Takjil', 'Es Batu', 'Lemon Tea', 'Indomie Goreng (J)', 
          'Indomie Goreng Aceh', 'Indomie Goreng (R)']  # Add all your items here
mlb.fit([mlb_classes])

@app.route('/recommend', methods=['POST'])
def recommend():
    try:
        # Validate the input JSON
        if not request.json or 'transaction' not in request.json:
            return jsonify({'error': 'Bad request, JSON must contain "transaction" key'}), 400

        transactions_data = request.json['transaction']
        if not all(isinstance(transaction, list) for transaction in transactions_data):
            return jsonify({'error': 'Each transaction must be a list of items'}), 400

        item_counter = Counter()

        for transaction in transactions_data:
            # Validate each transaction's items
            if not all(isinstance(item, str) for item in transaction):
                return jsonify({'error': 'All items in transaction must be strings'}), 400

            processed_data = process_transaction(transaction)
            predictions = model.predict(processed_data)
            item_counter.update(mlb.classes_[predictions.flatten() > 0.5])

        # Find the top 2 most common items
        top_two_items = [item for item, count in item_counter.most_common(2)]
        return jsonify({'recommendations': top_two_items})

    except Exception as e:
        app.logger.error(f"Error during recommendation: {e}")
        return jsonify({'error': 'Internal Server Error'}), 500

def process_transaction(transaction_data):
    # Ensure the transaction is not empty
    if not transaction_data:
        raise ValueError("Transaction data is empty")
    
    # Transform the transaction data into the binary format
    binary_transaction = mlb.transform([transaction_data])
    return binary_transaction

if __name__ == '__main__':
    app.run(debug=True)  # It's safer to specify the host and port
