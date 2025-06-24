const client = axios.create({
    baseURL: 'https://tewebhooks.azurewebsites.net/api/hotels',
    headers: {
       'APIKEY': '412436' 
    }
})

function getHotels(){
    return client.get('');
}

function reservationsByHotel(hotelID){
    return client.get(`/${hotelID}/reservations`);
}

export {getHotels, reservationsByHotel}