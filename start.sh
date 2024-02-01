echo "Register into DB.....\n"
curl --location 'http://localhost:8080/api/v1/auth/signup' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data-raw '{
    "email":"shirshak55@gmail.com",
    "password":"123456",
    "username":"shirshak55"
}'


echo "\nLogin into DB.....\n"
response=$(curl --location 'http://localhost:8080/api/v1/auth/login' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data '{
    "username":"shirshak55",
    "password":"123456"
}')

TOKEN=$(echo "$response" | jq -r '.authenticationToken')
echo $response
# Print the token value to verify
echo "Authentication Token: $TOKEN"


echo "Adding customers to db.......\n"
curl --location 'localhost:8080/customers' \
--header "Authorization: $TOKEN" \
--header 'Content-Type: application/json' \
--data '[
    {
        "name": "custom1"
    },
    {
        "name": "custom2"
    },
    {
        "name": "custom3"
    },
    {
        "name": "cusom4"
    }
]'



echo "\n getting all customers from db........\n"
curl --location 'localhost:8080/customers'

echo "\n Adding Purchase for customer  with id 1\n"

curl --location 'localhost:8080/customers/1/purchases' \
--header "Authorization: $TOKEN" \
--header 'Content-Type: application/json' \
--data '[
    {
        "amount": 120.00,
        "timestamp": "2022-10-05T02:41:37.909240026Z"
    },
    {
        "amount": 200.00,
        "timestamp": "2022-12-14T02:41:37.909240026Z"
    },
    {
        "amount": 150.00,
        "timestamp": "2023-01-02T02:41:37.909240026Z"
    },
    {
        "amount": 170.00,
        "timestamp": "2022-01-07T02:41:37.909240026Z"
    }
]'


echo "\n Adding Purchase for customer  with id 2...\n"

curl --location 'localhost:8080/customers/2/purchases' \
--header "Authorization: $TOKEN" \
--header 'Content-Type: application/json' \
--data '[
    {
        "amount": 1200.00,
        "timestamp": "2022-10-05T02:41:37.909240026Z"
    },
    {
        "amount": 2000.00,
        "timestamp": "2022-12-14T02:41:37.909240026Z"
    },
    {
        "amount": 1050.00,
        "timestamp": "2023-01-02T02:41:37.909240026Z"
    },
    {
        "amount": 1070.00,
        "timestamp": "2022-01-07T02:41:37.909240026Z"
    }
]'



echo "\n Adding Purchase for customer  with id 3...\n"

curl --location 'localhost:8080/customers/3/purchases' \
--header "Authorization: $TOKEN" \
--header 'Content-Type: application/json' \
--data '[
    {
        "amount": 1020.00,
        "timestamp": "2022-10-05T02:41:37.909240026Z"
    },
    {
        "amount": 20.00,
        "timestamp": "2022-12-14T02:41:37.909240026Z"
    },
    {
        "amount": 150.00,
        "timestamp": "2023-01-02T02:41:37.909240026Z"
    },
    {
        "amount": 190.00,
        "timestamp": "2022-01-07T02:41:37.909240026Z"
    }
]'

echo "\n Adding Purchase for customer  with id 4...\n"

curl --location 'localhost:8080/customers/4/purchases' \
--header "Authorization: $TOKEN" \
--header 'Content-Type: application/json' \
--data '[
    {
        "amount": 1020.00,
        "timestamp": "2022-10-05T02:41:37.909240026Z"
    },
    {
        "amount": 20.00,
        "timestamp": "2022-02-14T02:41:37.909240026Z"
    },
    {
        "amount": 150.00,
        "timestamp": "2023-05-02T02:41:37.909240026Z"
    },
    {
        "amount": 190.00,
        "timestamp": "2022-01-07T02:41:37.909240026Z"
    }
]'

echo "\n Fetching reward for point for customer with id 1\n"
curl --header "Authorization: $TOKEN" --location 'localhost:8080/rewards?customerId=1'


echo "\n Fetching reward all customers\n"
curl --header "Authorization: $TOKEN" --location 'localhost:8080/rewards'
