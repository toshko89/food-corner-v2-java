import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter } from 'react-router-dom';
import App from './App';
import reportWebVitals from './reportWebVitals';
import store from './app/store.js';
import { Provider } from 'react-redux';
import ScrollToTop from './hoc/ScrollToTop.js';
import { debounce } from "debounce";
import { saveState } from './app/localeStorage.js';

store.subscribe(
  debounce(() => {
    saveState(store.getState());
  }, 1000)
);

ReactDOM.render(
  <React.StrictMode>
    <Provider store={store}>
      <BrowserRouter>
        <ScrollToTop>
          <App />
        </ScrollToTop>
      </BrowserRouter>
    </Provider>
  </React.StrictMode>,
  document.getElementById('root')
);

reportWebVitals();
