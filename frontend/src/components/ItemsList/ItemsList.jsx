import React, { useContext, useState } from 'react';
import { AppContext } from '../../context/AppContext';
import { deleteItem } from '../../service/ItemService';
import toast from 'react-hot-toast';
import './ItemsList.css';

const ItemsList = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const { itemsData, setItemsData, setCategories } = useContext(AppContext);

  const filteredItems = itemsData.filter((item) =>
    item.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const removeItem = async (itemId, categoryId) => {
    try {
      const response = await deleteItem(itemId);
      if (response.status === 204) {
        const updatedItems = itemsData.filter(item => item.itemId !== itemId);
        setItemsData(updatedItems);
        setCategories(prevCategories =>
          prevCategories.map(category =>
            category.categoryId === categoryId
              ? { ...category, items: category.items - 1 }
              : category
          )
        );
        toast.success("Item deleted");
      } else {
        toast.error("Unable to delete item");
      }
    } catch (error) {
      console.error(error);
      toast.error("Unable to delete item");
    }
  };

  return (
    <div className="category-list-container">
      <div className="row pe-2">
        <div className="input-group mb-3 w-100">
          <input
            type="text"
            name="keyword"
            id="keyword"
            placeholder="Search by keyword"
            className="form-control"
            onChange={(e) => setSearchTerm(e.target.value)}
            value={searchTerm}
          />
          <span className="input-group-text bg-warning">
            <i className="bi bi-search"></i>
          </span>
        </div>
      </div>

      <div className="row g-3 pe-2">
        {filteredItems.map((item, index) => (
          <div className="col-12" key={index}>
            <div className="card p-3 bg-dark">
              <div className="d-flex align-items-start">
                <div className="me-3">
                  <img
                    src={item.imgUrl}
                    alt={item.name}
                    className="item-image"
                  />
                </div>

                <div className="flex-grow-1 text-break">
                  <h6 className="mb-1 text-white text-wrap">{item.name}</h6>
                  <p className="mb-0 text-white">
                    Category: {item.categoryName}
                  </p>
                  <span className="mb-0 text-block badge rounded-pill text-bg-warning">
                    &#8377; {item.price.toLocaleString('en-IN')}
                  </span>
                </div>

                <div className="ms-2 d-flex align-items-start flex-shrink-0">
                  <button
                    className="btn btn-danger btn-sm"
                    onClick={() => removeItem(item.itemId, item.categoryId)}
                  >
                    <i className="bi bi-trash"></i>
                  </button>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ItemsList;
