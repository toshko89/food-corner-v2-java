import React from 'react';
import Grid from '@mui/material/Grid';
import { v4 as uuidv4 } from 'uuid';
import { memo } from 'react';
import MenuCard from './MenuCard.js';

const Categories = memo(({ currentRestaurant, categories, products, deleteProductHandler, isOwner }) => {

  const uniqueCategories = [...new Set(categories)];
  return (
    <>
      {
        uniqueCategories.map(cat => {
          const categoryProduct = products.filter(product => product.category === cat)
          if (categoryProduct.length > 0) {
            return (
              <div key={uuidv4()}>
                <div className="row m-0">
                  <h3 className="p-3 m-0 bg-light w-100">{cat}</h3>
                  <div className="col-md-12 px-0 border-top"></div>
                  <Grid container spacing={{ xs: 2, md: 3 }} columns={{ xs: 4, sm: 8, md: 12 }}>
                    {categoryProduct.map(product => {
                      return (
                        <MenuCard key={product._id}
                          currentRestaurant={currentRestaurant}
                          isOwner={isOwner}
                          deleteProductHandler={deleteProductHandler}
                          product={product} />)
                    })}
                  </Grid>
                </div>
              </div>)
          } else {
            return <h2 className="font-weight-bold mb-3">No favorites yet</h2>
          }
        })
      }
    </>
  )
}, (prevProps, nextProps) => prevProps.currentRestaurant.products === nextProps.currentRestaurant.products)

export default Categories;