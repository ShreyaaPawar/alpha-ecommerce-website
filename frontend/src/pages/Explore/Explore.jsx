import React, { useContext, useState } from 'react';
import './Explore.css';
import { AppContext } from '../../context/AppContext';
import DisplayCategory from '../../components/DisplayCategory/DisplayCategory.jsx';
import DisplayItems from '../../components/DisplayItems/DisplayItems.jsx';
import CustomerForm from '../../components/CustomerForm/CustomerForm.jsx';
import CartItems from '../../components/CartItems/CartItems.jsx';
import CartSummary from '../../components/CartSummary/CartSummary.jsx';

const Explore = () => {
  const {categories} =  useContext(AppContext);
  const [selectCategory, setSelectCategory] = useState("");
  const [customerName, setCustomerName] = useState('');
  const [mobileNumber, setMobileNumber] = useState('');

  return (
    <div className="explore-container text-light">
      <div className="left-column">
        <div className="first-row" style={{ overflowY: 'auto' }}>
          <DisplayCategory 
            categories={categories}
            selectCategory={selectCategory}
            setSelectCategory={setSelectCategory}
          />
        </div>
        <hr className='horizontal-line' />
        <div className="second-row" style={{ overflowY: 'auto' }}>
          <DisplayItems
            selectedCategory={selectCategory}
          />
        </div>
      </div>

      <div className="right-column d-flex flex-column">
        <div className="customer-form-container" style={{ height: '15%' }}>
          <CustomerForm
            customerName={customerName}
            mobileNumber={mobileNumber}
            setCustomerName={setCustomerName}
            setMobileNumber={setMobileNumber}
          />
        </div>
        <hr className='my-3 text-light' />
        <div className="cart-items-container" style={{ height: '55%', overflowY: 'auto',  overflowX: 'hidden' }}>
          <CartItems/>
        </div>
        <div className="cart-summary-container" style={{ height: '30%' }}>
          <CartSummary
            customerName={customerName}
            mobileNumber={mobileNumber}
            setCustomerName={setCustomerName}
            setMobileNumber={setMobileNumber}
          />
        </div>
      </div>
    </div>
  )
}

export default Explore
