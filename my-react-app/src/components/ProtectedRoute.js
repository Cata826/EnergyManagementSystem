
import React from 'react';
import Unauthorized from './Unauthorized'; 

const ProtectedRoute = ({ element, requiresAdmin, requiresNonAdmin }) => {

    const storedIsAdmin = localStorage.getItem('admin') === 'true';

    if (requiresAdmin && !storedIsAdmin) {
        return <Unauthorized />;
    }

    if (requiresNonAdmin && storedIsAdmin) {
        return <Unauthorized />;
    }
 
    return element;
};

export default ProtectedRoute;
