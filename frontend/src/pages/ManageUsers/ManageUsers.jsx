import React, { use, useEffect, useState } from 'react';
import './ManageUsers.css';
import UsersForm from '../../components/UsersForm/UsersForm';
import UsersList from '../../components/UsersList/UsersList';
import { fetchUsers } from '../../service/UserService.jsx';
import toast from 'react-hot-toast';

const ManageUsers = () => {
  const[users, setUsers] = useState([]);
  const[loading, setLoading] = useState(false);

  useEffect(() => {
    async function fetchUser() {
      try{
        setLoading(true);
        const response = await fetchUsers();
        setUsers(response.data);
      } catch(error) {
        console.error(error);
        toast.error("Unable to fetch users")
      } finally {
        setLoading(false);
      }
    }
    fetchUser();
  }, []);

  return (
    <div className="users-container text-light">
        <div className="left-column">
            <UsersForm setUsers= {setUsers} />
        </div>

        <div className="right-column">
            <UsersList users={users} setUsers={setUsers} />
        </div>
    </div>
  )
}

export default ManageUsers
