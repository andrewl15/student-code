const client = axios.create({
    baseURL: 'https://tewebhooks.azurewebsites.net/api/reservations',
    headers: {
        'APIKEY': '412436'
    }
})

function deleteReservation(resID){
    return client.delete(`/${resID}`);
}

function addReservation(newReservation){
    return client.post('',newReservation);
}

function updateReservation(editedReservation){
    return client.put(`/${editedReservation.id}`,editedReservation);
}

export {deleteReservation, addReservation, updateReservation}