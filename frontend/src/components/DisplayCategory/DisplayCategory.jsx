import './DisplayCategory.css';
import React from 'react';
import Category from '../Category/Category.jsx';
import {assets} from '../../assets/assets.jsx';

const DisplayCategory = ({categories, selectCategory, setSelectCategory}) => {
  return (
    <div className="row g-3" style={{width: '100%', margin: 0}}>
      <div key={"all"} className="col-md-4 col-sm-6" style={{padding: '0px 10px'}}>
        <Category 
              categoryName = "All Items"
              imgUrl = {assets.device}
              numberOfItems = {categories.reduce((acc, cat) => acc + cat.items, 0)}
              bgColor = "#6c757d"
              isSelected = {selectCategory === ""}
              onClick = {() => setSelectCategory("")}
            />
      </div>
      {categories.map(category => (
        <div key={category.categoryId} className="col-md-4 col-sm-6" style={{padding: '0px 10px'}}>
          <Category 
             categoryName = {category.name}
             imgUrl = {category.imgUrl}
             numberOfItems = {category.items}
             bgColor = {category.bgColor}
             isSelected = {selectCategory === category.categoryId}
             onClick = {() => setSelectCategory(category.categoryId)}
          />
        </div>
      ))}
    </div>
  )
}

export default DisplayCategory
