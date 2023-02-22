import { Modal, Text, Input } from "@nextui-org/react";
import { LoadingButton } from '@mui/lab';
import { useEffect, useState } from "react";
import { useDispatch } from 'react-redux';
import { useParams } from "react-router-dom";
import { setRestaurantState } from "../../app/restaurant.js";
import { addProduct, editProduct } from "../../services/productService.js";
import stringCount from "../../utils/minStringCount.js";
import SendIcon from '@mui/icons-material/Send';

export default function AddProductModal({ setVisible, visible, product }) {

  const { id } = useParams();
  const dispatch = useDispatch();
  const [recipe, setRecipe] = useState({
    name: '', ingredients: '',
    weight: '', price: '', category: ''
  });
  const [file, setFile] = useState([]);
  const [error, setError] = useState(false);
  const [loading, setLoading] = useState(false);

  const closeHandler = () => {
    setVisible(false);
    setRecipe({
      name: '', ingredients: '',
      weight: '', price: '', category: ''
    });
    setFile([]);
    setError(false);
  };

  function handleFileChange(e) {
    const file = e.target.files[0];
    setFile(file);
  }

  useEffect(() => {
    if (product) {
      setRecipe({
        name: product.name.toString(),
        ingredients: product.ingredients.join(','),
        weight: product.weight.toString(),
        price: product.price.toString(),
        category: product.category.toString()
      })
    }
    return () => {
      closeHandler();
    }
  }, [product])

  async function submitProduct() {
    if (recipe.name.trim() === '' || recipe.ingredients.trim() === '' || recipe.weight.trim() === ''
      || recipe.price.trim() === '' || recipe.category.trim() === '') {
      setError('All fields are required');
      return;
    }
    if (!stringCount(recipe.ingredients)) {
      setError('At least 3 ingredients');
      return;
    }
    if (file.length === 0) {
      setError('Please add product photo');
      return;
    }

    const data = new FormData();
    data.append('image', file);
    data.append('name', recipe.name);
    data.append('ingredients', recipe.ingredients);
    data.append('weight', recipe.weight);
    data.append('price', recipe.price);
    data.append('category', recipe.category)


    let res;

    try {
      setLoading(true);
      if (product) {
        res = await editProduct(id, product.id, data);  
      } else {
        res = await addProduct(id, data);
      }
      if (res.status !== 200 && res.status !== 201) {
        setError(res.message);
        setLoading(false);
        return;
      }
      dispatch(setRestaurantState(res.data));
      setLoading(false);
      setVisible(false);
    } catch (error) {
      setLoading(false);
      setError(error)
    }
  }

  return (
    <Modal aria-label="modal-title" open={visible} onClose={closeHandler}>
      <Modal.Header aria-label="modal-header">
        <Text b id="modal-title" size={20}>
          {product ? 'Edit recipe' : 'Add recipe'}
        </Text>
      </Modal.Header>
      <Modal.Body aria-label="modal-body">
        <Input aria-label="modal-name"
          onChange={(e) => setRecipe({ ...recipe, name: e.target.value })}
          value={recipe.name}
          onBlur={() => setError(false)}
          clearable
          bordered
          fullWidth
          color="primary"
          size="lg"
          placeholder="Recipe name"
        />
        <Input aria-label="modal-ingredients"
          onChange={(e) => setRecipe({ ...recipe, ingredients: e.target.value })}
          value={recipe.ingredients}
          onBlur={() => setError(false)}
          clearable
          bordered
          fullWidth
          color="primary"
          size="lg"
          placeholder="At least 3 ingredients separated by ','"
        />
        <Input aria-label="modal-weight"
          type="number"
          onChange={(e) => setRecipe({ ...recipe, weight: e.target.value })}
          onBlur={() => setError(false)}
          value={recipe.weight}
          clearable
          bordered
          fullWidth
          color="primary"
          size="lg"
          placeholder="Weight in grams"
        />
        <Input aria-label="modal-price"
          type="number"
          onChange={(e) => setRecipe({ ...recipe, price: e.target.value })}
          onBlur={() => setError(false)}
          value={recipe.price}
          clearable
          bordered
          fullWidth
          color="primary"
          size="lg"
          placeholder="Price"
        />
        <Input aria-label="modal-category"
          onChange={(e) => setRecipe({ ...recipe, category: e.target.value })}
          onBlur={() => setError(false)}
          value={recipe.category}
          clearable
          bordered
          fullWidth
          color="primary"
          size="lg"
          placeholder="Category"
        />
        <Input aria-label="modal-file"
          onChange={handleFileChange}
          onBlur={() => setError(false)}
          type="file"
          clearable
          bordered
          fullWidth
          color="primary"
          size="lg"
          placeholder="Recipe picture"
        />
        {error && <Text color="red" size={20}>{error}</Text>}
      </Modal.Body>
      <Modal.Footer aria-label="modal-footer">
        <LoadingButton
          disabled={error !== false}
          onClick={submitProduct}
          endIcon={<SendIcon />}
          loading={loading}
          loadingPosition="end"
          variant="contained"
        >
          {product ? 'Edit' : 'Add'}
        </LoadingButton>
      </Modal.Footer>
    </Modal>
  );
}
