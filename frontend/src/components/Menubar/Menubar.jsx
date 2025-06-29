import React, { useContext, useState } from 'react';
import './Menubar.css';
import { assets } from '../../assets/assets.jsx';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { AppContext } from '../../context/AppContext.jsx';

const Menubar = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const { auth, setAuthData } = useContext(AppContext);
    const isAdmin = auth.role === 'ROLE_ADMIN';

    const [dropdownOpen, setDropdownOpen] = useState(false);

    const logout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('role');
        setAuthData(null, null, null);
        navigate('/login');
    };

    const isActive = (path) => location.pathname === path;

    const toggleDropdown = () => setDropdownOpen(!dropdownOpen);
    const closeDropdown = () => setDropdownOpen(false);

    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark px-2">
            <Link className="navbar-brand" to="#">
                <img src={assets.logo} alt="Logo" height="40" />
            </Link>
            <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse p-2" id="navbarNav">
                <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                    <li className="nav-item">
                        <Link className={`nav-link ${isActive('/dashboard') ? 'fw-bold text-warning' : ''}`} to="/dashboard">Dashboard</Link>
                    </li>
                    <li className="nav-item">
                        <Link className={`nav-link ${isActive('/explore') ? 'fw-bold text-warning' : ''}`} to="/explore">Explore</Link>
                    </li>
                    {isAdmin && (
                        <>
                            <li className="nav-item">
                                <Link className={`nav-link ${isActive('/items') ? 'fw-bold text-warning' : ''}`} to="/items">Manage Items</Link>
                            </li>
                            <li className="nav-item">
                                <Link className={`nav-link ${isActive('/category') ? 'fw-bold text-warning' : ''}`} to="/category">Manage Categories</Link>
                            </li>
                            <li className="nav-item">
                                <Link className={`nav-link ${isActive('/users') ? 'fw-bold text-warning' : ''}`} to="/users">Manage Users</Link>
                            </li>
                        </>
                    )}
                    <li className="nav-item">
                        <Link className={`nav-link ${isActive('/orders') ? 'fw-bold text-warning' : ''}`} to="/orders">Order History</Link>
                    </li>
                </ul>
                <div className="nav-profile position-relative" onBlur={closeDropdown} tabIndex={0}>
                    <img
                        src={assets.profile}
                        alt="Profile"
                        height={32}
                        width={32}
                        className="rounded-circle profile-icon"
                        onClick={toggleDropdown}
                        style={{ cursor: 'pointer' }}
                    />
                    {dropdownOpen && (
                        <div className="profile-dropdown">
                            <div className="dropdown-item mb-1">
                                <i className="bi bi-person-circle me-2"></i>{auth.username}
                            </div>
                            <div className="dropdown-item" onClick={logout}>
                                <i className="bi bi-box-arrow-right me-2"></i>Logout
                            </div>
                        </div>
                    )}
                </div>
            </div>
        </nav>
    );
};

export default Menubar;
