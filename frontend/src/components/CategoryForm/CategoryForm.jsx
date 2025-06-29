import React, { useContext, useEffect, useState } from 'react'
import { assets } from '../../assets/assets.jsx';
import { addCategory } from '../../service/CategoryService.jsx';
import { AppContext } from '../../context/AppContext';
import toast from 'react-hot-toast';

const CategoryForm = () => {
    const{setCategories, categories} = useContext(AppContext);

    const [loading, setLoading] = useState(false);

    const [image, setImage] = useState(false);

    const [data, setData] = useState({
        name : "",
        description: "",
        bgColor: "#2c2c2c"
    });

    const onChangeHandler = (e) => {
        const value = e.target.value;
        const name = e.target.name;
        setData(
            (prevData) => (
                {
                    ...prevData, [name] : value
                }
            )
        );
    }

    useEffect(() => {
        console.log(data);
    }, [data]);

    const onSubmitHandler = async (e) => {
        e.preventDefault();
        if(!image){
            toast.error("Select image for category");
            return;
        }
        setLoading(true);
        const formData = new FormData();
        formData.append("category", JSON.stringify(data));
        formData.append("file", image);
        try{
            const response = await addCategory(formData);
            console.log(response);
            if(response.status === 201 || response.status === 200){
                // setCategories([...setCategories, response.data]);
                setCategories((prevCategories) => [...prevCategories, response.data]);
                toast.success("Category added");
                setData(
                    {
                        name: "",
                        description: "",
                        bgColor: "#2c2c2c"
                    }
                );
                setImage(false);
            }
        }catch(error){
            toast.error("Error adding category")
        }finally{
            setLoading(false);
        }
    }

    return (
        <div className='item-form-container' style={{ height: '100vh', overflow: 'auto', overflowX: 'hidden' }}>
            <div className="mx-2 mt-2">
                <div className="row">
                    <div className="card col-md-12 form-container">
                        <div className="card-body">
                            <form onSubmit={onSubmitHandler}>
                                <div className="mb-3">
                                    <label htmlFor="image" className='form-label'>
                                        <img src={image ? URL.createObjectURL(image) : assets.upload } 
                                             alt="" 
                                             width={48}/>
                                    </label>
                                    <input type="file" 
                                           onChange={(e) => setImage(e.target.files[0])} 
                                           name="image" 
                                           id="image" 
                                           className='form-control' 
                                           hidden />
                                </div>

                                <div className="mb-3">
                                    <label htmlFor="name" className='form-label'>
                                        Name
                                    </label>
                                    <input type="text" 
                                           name="name" 
                                           id="name" 
                                           className='form-control' 
                                           placeholder='Category Name' 
                                           onChange={onChangeHandler}
                                           value={data.name}
                                           required
                                    />
                                </div>

                                <div className="mb-3">
                                    <label htmlFor="description" className='form-label'>
                                        Description
                                    </label>
                                    <textarea rows={5} 
                                              type="text" 
                                              name="description" 
                                              id="description" 
                                              className='form-control' 
                                              placeholder='Write content here...' 
                                              onChange={onChangeHandler}
                                              value={data.description}
                                    />
                                </div>

                                <div className="mb-3">
                                    <label htmlFor="bgColor" className='form-label'>
                                        Background Color
                                    </label><br />
                                    <input type="color" 
                                           name="bgColor" 
                                           id="bgColor" 
                                           placeholder='#ffffff' 
                                           onChange={onChangeHandler}
                                           value={data.bgColor}
                                    />
                                </div>

                                <button type="submit" 
                                        className='btn w-100 btn-warning'
                                        disabled={loading}
                                >
                                    {loading ? "Loading..." : "Submit"}
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default CategoryForm
