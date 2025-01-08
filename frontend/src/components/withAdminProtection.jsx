import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

const withAdminProtection = (WrappedComponent) => {
  return (props) => {
    const [isLoading, setIsLoading] = useState(true);
    const [isAdmin, setIsAdmin] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
      const fetchUserRole = async () => {
        try {
          const response = await axios.get('/user');
          const user = response.data.user;
          const roles = response.data.groups.map(group => group.role.name);
          if (roles.includes('Admin')) {
            setIsAdmin(true);
          } else {
            toast.error('Access denied. Admins only.');
            navigate('/dashboard');
          }
        } catch (error) {
          toast.error('Failed to fetch user role');
          navigate('/');
        } finally {
          setIsLoading(false);
        }
      };

      fetchUserRole();
    }, [navigate]);

    if (isLoading) {
      return <div>Loading...</div>;
    }

    return isAdmin ? <WrappedComponent {...props} /> : null;
  };
};

export default withAdminProtection;