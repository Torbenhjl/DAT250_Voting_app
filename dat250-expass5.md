Installation:
![image](https://github.com/user-attachments/assets/3b54f27d-0df5-419f-8e6e-8faf74fc7d1f)

Experiment 1 crud operations:

Insert:
![image](https://github.com/user-attachments/assets/2d9629f4-b3b1-4654-8936-5934724f1290)

query documents:
![image](https://github.com/user-attachments/assets/cc07b689-8b97-4299-b793-293def969674)

update documents:

![image](https://github.com/user-attachments/assets/2e9bb6e5-9b73-4e6f-b5d0-a651c097e0e8)

delete:
![image](https://github.com/user-attachments/assets/452328ec-494b-42e7-b74e-b7c4f4ebb654)

Bulk-write operations:
![image](https://github.com/user-attachments/assets/c11a6656-be57-4319-9d40-7b939de264e8)


Experiment 2 Aggregation:

![image](https://github.com/user-attachments/assets/8d1948d5-baf8-4c86-85fc-5acb1becc51b)

my aggregation function:
![image](https://github.com/user-attachments/assets/4443b1e9-5245-4069-960a-b72e6dbed079)

The function first uses the $unwind to break the items array into individual documents so that each item can be processed spererately.
Then $group groups the documents by customer id to aggregate data per customer.
$sum and $multiply calculates the total revenue per customer by multiplying the quantity and price for each item and summing them up for each customer.

This function is useful because it calculates total revenue per customer by aggregating data from their orders, helping businesses analyze sales, track customer spending, and make informed decisions based on customer purchasing patterns. It simplifies complex data into actionable insights.








