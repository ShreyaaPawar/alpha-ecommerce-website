import { useContext, useState } from 'react';
import './App.css';
import Menubar from './components/Menubar/Menubar';
import { Navigate, Route, Routes, useLocation } from 'react-router-dom';
import Dashboard from './pages/Dashboard/Dashboard';
import Explore from './pages/Explore/Explore';
import ManageItems from './pages/ManageItems/ManageItems';
import ManageCategory from './pages/ManageCategory/ManageCategory';
import ManageUsers from './pages/ManageUsers/ManageUsers';
import { Toaster } from 'react-hot-toast';
import Login from './pages/Login/Login';
import OrderHistory from './pages/OrderHistory/OrderHistory';
import { AppContext } from './context/AppContext';
import NotFound from './pages/NotFound/NotFound';

function App() {
  const location = useLocation();
  const { auth } = useContext(AppContext);

  const loginRoute = ({ element }) => {
    if (auth.token) {
      return <Navigate to='/dashboard' replace />;
    }
    return element;
  }

  const ProtectedRoute = ({ element, allowedRoles }) => {
    if (!auth.token) {
      return <Navigate to='/login' replace />;
    }

    if (allowedRoles && !allowedRoles.includes(auth.role)) {
      return <Navigate to='/dashboard' replace />;
    }

    return element;
  }

  return (
    <>
      <div>
        {location.pathname !== '/login' && <Menubar />}
        <Toaster />
        <Routes>
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/explore" element={<Explore />} />

          {/* admin only routes */}
          <Route path="/items" element={<ProtectedRoute element={<ManageItems />} allowedRoles={['ROLE_ADMIN']} />} />
          <Route path="/category" element={<ProtectedRoute element={<ManageCategory />} allowedRoles={['ROLE_ADMIN']} />} />
          <Route path="/users" element={<ProtectedRoute element={<ManageUsers />} allowedRoles={['ROLE_ADMIN']} />} />
          
          <Route path="/login" element={<Login />} />
          <Route path="/orders" element={<OrderHistory />} />
          <Route path="/" element={<Dashboard />} />
          <Route path="*" element={<NotFound />} />

        </Routes>
      </div>
    </>
  )
}

export default App
