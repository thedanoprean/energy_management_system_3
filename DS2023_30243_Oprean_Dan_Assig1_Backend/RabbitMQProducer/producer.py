import pandas as pd
import time
import calendar
import pika
import json
import os

from datetime import datetime
from dotenv import load_dotenv

connection = pika.BlockingConnection(pika.ConnectionParameters(host="localhost"))
channel = connection.channel()

try:
    channel.queue_declare(queue = "energy-queue")
    print("Queue 'energy-queue' declared successfully.")
except Exception as e:
    print(f"Failed to declare queue: {e}")


load_dotenv()

data = pd.read_csv("sensor.csv")

for index, row in data.iterrows():

    current_time = time.gmtime()
    timestamp = calendar.timegm(current_time)

    body = {
        "timestamp": timestamp,
        "deviceId": os.environ["deviceId"],
        "measurement": row["0"],
        "userId": os.environ["userId"],
    }

    channel.basic_publish(
        exchange="", routing_key="energy-queue", body=json.dumps(body)
    )
    print("Sent: " + json.dumps(body))

    time.sleep(3)

connection.close()