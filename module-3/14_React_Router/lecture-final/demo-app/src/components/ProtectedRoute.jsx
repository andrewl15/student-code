import { Navigate } from 'react-router-dom';
import { useContext } from 'react';
import { UserContext } from '../context/UserContext';

// TODO: Configure

// TODO: Add Navigation Link back to login if not authenticated

export default function ProtectedRoute({ children }) {

    const user = useContext(UserContext);

    if(user) {
        return children;
    }

    return <Navigate to= "/login" />;

};
