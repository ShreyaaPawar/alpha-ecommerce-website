import React from 'react';
import './ManageItems.css';
import ItemsForm from '../../components/ItemsForm/ItemsForm';
import ItemsList from '../../components/ItemsList/ItemsList';

const ManageItems = () => {
  return (
    <div className="items-container text-light">
        <div className="left-column">
          <div className="items-form-scrollable">
            <ItemsForm/>
          </div>
        </div>

        <div className="right-column">
            <ItemsList/>
        </div>
    </div>
  )
}

export default ManageItems
