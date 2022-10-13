Playing with reactive paradigm using `System integration POC`

1. GET [200 OK -> SUCCESS]
    a. request to fetch data
        i. URI [/resources/{idOfResource}]
    b. request to fetch sized data with query string usage
        i. URI [/resources?pageNumber=pageNumberValue&pageSize=pageSizeValue]
            - `pageNumber`: numbering will start from 1 and will act as a cursor over the requested data
            - `sort` [is part of UI presentation concern and it's better not to be included in this context]
    c. requests to mimc system errors
        i. URI [/resources/client_error] -> [400 BAD_REQUEST]
        ii. URI [/resources/server_error] -> [500 INTERNAL_SERVER_ERROR]
        iii. URI [/resources/other_error] -> [500 INTERNAL_SERVER_ERROR]
2. POST [201 CREATED -> SUCCESS] with location header of the created resource [/resources/{idOfResource}] in case of future interest
    a. request to create new data over backend side that holds a data inside its payload
        i. URI [/resources]
    b. requests to mimc system errors
        i. URI [/resources/client_error] -> [400 BAD_REQUEST]
        ii. URI [/resources/server_error] -> [500 INTERNAL_SERVER_ERROR]
        iii. URI [/resources/other_error] -> [500 INTERNAL_SERVER_ERROR]
3. DELETE [204 NO_CONTENT -> SUCCESS]
    a. request to delete data over backend side
        i. URI [/resources/{idOfResource}]
    b. requests to mimc system errors
        i. URI [/resources/client_error] -> [400 BAD_REQUEST]
        ii. URI [/resources/server_error] -> [500 INTERNAL_SERVER_ERROR]
        iii. URI [/resources/other_error] -> [500 INTERNAL_SERVER_ERROR]
4. PATCH [204 NO_CONTENT -> SUCCESS]
    a. request to change data over backend side that holds a new state of this data inside its payload
        i. URI [/resources]
    b. requests to mimc system errors
        i. URI [/resources/client_error] -> [400 BAD_REQUEST]
        ii. URI [/resources/server_error] -> [500 INTERNAL_SERVER_ERROR]
        iii. URI [/resources/other_error] -> [500 INTERNAL_SERVER_ERROR]