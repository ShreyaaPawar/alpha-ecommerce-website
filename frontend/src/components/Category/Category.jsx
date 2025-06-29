import './Category.css';
import React from 'react';

const Category = ({categoryName, imgUrl, numberOfItems, bgColor, isSelected, onClick}) => {
  return (
    <div className="d-flex align-items-center p-3 h-100 rounded gap-1 position-relative category-hover"
    style={{backgroundColor: bgColor, cursor: 'pointer'}} onClick={onClick}>
      <div style={{position: 'relative', marginRight: '15px'}}>
        <img src={imgUrl} alt={categoryName} className='category-image'/>
      </div>

      <div>
        <h5 className="text-white mb-0">
          {categoryName}
        </h5>
        <p className="text-white mb-0">
          {numberOfItems} Items
        </p>
      </div>

      {isSelected && <div className='active-category'></div>}
    </div>
  )
}

export default Category
