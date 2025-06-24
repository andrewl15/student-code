import axios from "axios";

export default {
    
    getCelestialObjects() {
        return axios.get('https://teapi.netlify.app/api/celestial-objects');
    }
}