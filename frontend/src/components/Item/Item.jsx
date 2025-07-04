import { AppContext } from '../../context/AppContext';
import './Item.css';

import React, { useContext } from 'react'

const Item = ({itemName, itemPrice, itemImage, itemId}) => {
    const {addTocart} = useContext(AppContext);
    const handleAddToCart = () => {
        addTocart({
            name: itemName,
            price: itemPrice,
            quantity: 1,
            itemId: itemId
        });
    }
    
  return (
    <div className="p-3 bg-dark rounded shadow-sm h-100 d-flex align-items-center item-card">
        <div style={{position: 'relative', marginRight: '15px'}}>
            <img src={itemImage} alt={itemName}  className='item-image'/>
        </div>

        <div className="flex-grow-1 ms-2">
            <h6 className="mb-1 text-light">
                {itemName}
            </h6>
            <p className="mb-0 fw-bold text-light">
                &#8377;{itemPrice.toLocaleString('en-IN')}
            </p>
        </div>

        <div className="d-flex flex-column justify-content-between align-items-center ms-3"
        style={{height: '100%'}}>
            <i className="bi bi-cart-plus fs-4 text-warning"></i>
            
            <button className="btn btn-success btn-sm" onClick={handleAddToCart}>
                <i className="bi bi-plus"></i>
            </button>
        </div>
    </div>
  )
}

export default Item
