import { useNavigate, useParams } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import AppContext from "../Context/Context";
import axios from "../axios";

const Product = () => {
  const { id } = useParams();
  const { data, addToCart, removeFromCart, cart, refreshData } = useContext(AppContext);
  const [product, setProduct] = useState(null);
  const [imageUrl, setImageUrl] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/products/${id}`);
        setProduct(response.data);
        if (response.data.imageName) {
          fetchImage(response.data.imageName);
        }
      } catch (error) {
        console.error("Error fetching product:", error);
      }
    };

    const fetchImage = async (imageName) => {
      try {
        const response = await axios.get(`http://localhost:8080/api/products/${id}/image`, { responseType: "blob" });
        setImageUrl(URL.createObjectURL(response.data));
      } catch (error) {
        console.error("Error fetching image:", error);
      }
    };

    fetchProduct();
  }, [id]);

  const deleteProduct = async () => {
    try {
      await axios.delete(`http://localhost:8080/api/products/${id}`);
      removeFromCart(id);
      alert("Product deleted successfully");
      refreshData();
      navigate("/");
    } catch (error) {
      console.error("Error deleting product:", error);
    }
  };

  const handleEditClick = () => {
    navigate(`/product/update/${id}`);
  };

  const handleAddToCart = () => {
    addToCart(product);
    alert("Product added to cart");
  };

  if (!product) {
    return <h2 className="text-center" style={{ padding: "10rem" }}>Loading...</h2>;
  }

  return (
    <div className="containers">
      <img className="left-column-img" src={imageUrl} alt={product.imageName || "Product Image"} />
      <div className="right-column">
        <div className="product-description">
          <span>{product.category}</span>
          <h1>{product.name}</h1>
          <h5>{product.brand}</h5>
          <p>{product.description}</p>
        </div>

        <div className="product-price">
          <span>{`$${product.price}`}</span>
          <button
  className={`cart-btn ${!product.available ? "disabled-btn" : ""}`}
  onClick={handleAddToCart}
  disabled={!product.available}
>
  {product.available ? "Add to Cart" : "Out of Stock"}
</button>

          <h6>Stock Available: <i style={{ color: "green", fontWeight: "bold" }}>{product.quantity}</i></h6>
          <h6>Product listed on:</h6>
          <i>{new Date(product.releasedate).toLocaleDateString()}</i>
        </div>

        <div className="update-button">
          <button className="btn btn-primary" type="button" onClick={handleEditClick}>Update</button>
          <button className="btn btn-danger" type="button" onClick={deleteProduct}>Delete</button>
        </div>
      </div>
    </div>
  );
};

export default Product;
