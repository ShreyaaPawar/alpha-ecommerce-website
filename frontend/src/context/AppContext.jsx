import { createContext, useEffect, useState } from "react";
import { fetchCategories } from "../service/CategoryService.jsx";
import { fetchItems } from "../service/ItemService.jsx";

export const AppContext = createContext(null);

export const AppContextProvider = (props) => {
    const[categories, setCategories] = useState([]);
    const[itemsData, setItemsData] = useState([]);
    const[auth, setAuth] = useState({token: null, role: null, username: null});
    const [cartItems, setCartItems] = useState([]);
    const addTocart = (item) => {
        const existingItem = cartItems.find(cartItem => cartItem.name === item.name);
        if(existingItem) {
            setCartItems(cartItems.map(cartItem => cartItem.name === item.name ? {...cartItem, quantity: cartItem.quantity + 1} : cartItem));
        }else {
            setCartItems([...cartItems, {...item, quantity: 1}]);
        }
    }
    const removeFromCart = (itemId) => {
        setCartItems(cartItems.filter(item => item.itemId != itemId));
    }
    const updateQuantity = (itemId, newQuantity) => {
        setCartItems(cartItems.map(item => item.itemId === itemId ? {...item, quantity: newQuantity} : item));
    }
    useEffect( () => {
        async function loadData() {
            if(localStorage.getItem('token') && localStorage.getItem('role')){
                setAuthData(
                    localStorage.getItem('token'),
                    localStorage.getItem('role')
                );
            }
            const response = await fetchCategories();
            const itemResponse = await fetchItems();
            setCategories(response.data);
            setItemsData(itemResponse.data);
        }

        loadData();
    }, []);

    const clearCart =() => {
        setCartItems([]);
    }

    const setAuthData = (token, role, username) => {
        setAuth({token, role, username});
    }

    const contextValue = {
        categories,
        setCategories,
        auth, 
        setAuthData,
        itemsData,
        setItemsData,
        addTocart,
        cartItems,
        removeFromCart,
        updateQuantity,
        clearCart
    }

    return <AppContext.Provider value={contextValue}>
        {props.children}
    </AppContext.Provider>
}
