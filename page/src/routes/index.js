import React, { PropTypes } from 'react';
import { Router, Route, IndexRoute, Link, hashHistory} from 'react-router';
import App from '../components/App';
import Post from '../components/common/Post.jsx';
import NotFound from '../components/NotFound';

const Routes = () =>
  <Router history={hashHistory}>
    <Route path="/" component={App} />
    <Route path="/post" component={Post} />
    <Route path="*" component={NotFound}/>
  </Router>;

Routes.propTypes = {
  history: PropTypes.any,
};

export default Routes;
