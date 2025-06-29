import { AppContext } from '../../context/AppContext';
import './DisplayItems.css';
import React, { useContext, useState } from 'react';
import Item from '../Item/Item.jsx';
import SearchBox from '../SearchBox/SearchBox.jsx';

const DisplayItems = ({selectedCategory}) => {
  const {itemsData} = useContext(AppContext);
  const[searchText, setSearchText] = useState("");
  const filteredItems = itemsData.filter(item => {
    if(!selectedCategory) return true;
    return item.categoryId === selectedCategory;
  }).filter(item => item.name.toLowerCase().includes(searchText.toLowerCase()));

  return (
    <div className="p-3">
      <div className="d-flex justify-content-between align-items-center align-items-center mb-4">
        <div></div>
        <div>
          <SearchBox onSearch={setSearchText}></SearchBox>
        </div>
      </div>
      <div className="row g-3">
        {
          filteredItems.length > 0 ? (
            filteredItems.map(
              (item, index) => (
                <div key={index} className='col-md-4 col-sm-6'>
                  <Item 
                    itemName={item.name}
                    itemPrice={item.price}
                    itemImage={item.imgUrl}
                    itemId={item.itemId}
                  />
                </div>
              )
            )
          ) : (
            <div className="no-items-message text-center mt-3" style={{color: '#888'}}>
              <h4 style={{fontSize: '18px'}}>No items found</h4>
            </div>
          )
        }
      </div>
    </div>
  )
}

export default DisplayItems
