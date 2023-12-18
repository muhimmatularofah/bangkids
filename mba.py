import numpy as np
import pandas as pd
import tensorflow as tf
from sklearn.preprocessing import MultiLabelBinarizer

def train_and_extract_association_rules(data, threshold=0.005):
    # Convert the data into a binary matrix (one-hot encoding)
    mlb = MultiLabelBinarizer()
    data_binary = mlb.fit_transform(data)

    # Define the neural network model
    model = tf.keras.Sequential([
        tf.keras.layers.Embedding(input_dim=data_binary.shape[1], output_dim=2, input_length=data_binary.shape[1]),
        tf.keras.layers.Flatten(),
        tf.keras.layers.Dense(units=64, activation='relu'),
        tf.keras.layers.Dense(units=data_binary.shape[1], activation='sigmoid')
    ])

    # Compile the model
    model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])

    # Train the model
    model.fit(data_binary, data_binary, epochs=50, batch_size=1)

    # Get item embeddings from the trained model
    item_embeddings = model.layers[0].get_weights()[0]

    # Display item embeddings
    item_embedding_df = pd.DataFrame(item_embeddings, index=mlb.classes_)

    # Extract association rules based on learned patterns
    association_rules = []

    for i, item in enumerate(mlb.classes_):
        if i < item_embedding_df.shape[1]:
            rules = item_embedding_df.index[item_embedding_df.iloc[:, i] > threshold].tolist()
            rules = [rule for rule in rules if rule != item and rule in mlb.classes_]  # Exclude the item itself and check existence
            for rule in rules:
                association_rules.append((item, rule))

    return association_rules, mlb, model

if __name__ == '__main__':
    # Read the data from CSV file
    df = pd.read_csv('semua_transaksi_ch11b.csv', delimiter=';')

    # Assuming 'TransactionID' is the correct column name
    transactions = df.groupby('TransactionID')['FoodItem'].apply(list).reset_index(name='Food_Items')
    data = transactions['Food_Items'].tolist()

    # Train the model and extract association rules
    association_rules, mlb, model = train_and_extract_association_rules(data)

    # Print association rules with 2 or more items
    if len(association_rules) > 0:
        for rule in association_rules:
            if len(rule) >= 2:
                print(" => ".join(rule))
    else:
        print("No association rules found.")

    # Save the model
    model.save("model_MBA.h5")
