import './CustomerForm.css';
import React from 'react';

const CustomerForm = ({customerName, mobileNumber, setCustomerName, setMobileNumber}) => {
  return (
    <div className="p-2 h-100 d-flex flex-column justify-content-center">
      <div className="mb-2 row align-items-center">
        <label htmlFor="customerName" className="col-5 col-form-label text-white pe-2">
          Customer Name
        </label>
        <div className="col-7">
          <input type="text" className="form-control form-control-sm" id="customerName" onChange={(e) => setCustomerName(e.target.value)} value={customerName} required/>
        </div>
      </div>

      <div className="mb-0 row align-items-center">
        <label htmlFor="mobileNumber" className="col-5 col-form-label text-white pe-2">
          Mobile Number
        </label>
        <div className="col-7">
          <input type="text" className="form-control form-control-sm" id="mobileNumber" onChange={(e) => setMobileNumber(e.target.value)} value={mobileNumber} required/>
        </div>
      </div>
    </div>
  );
};

export default CustomerForm;
