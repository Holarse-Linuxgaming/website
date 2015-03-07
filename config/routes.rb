Rails.application.routes.draw do

  get 'search/index', as: :search

  get 'about' => 'static_page#about', as: :about
  get 'imprint' => 'static_page#imprint', as: :imprint
  get 'privacy' => 'static_page#privacy', as: :privacy

  resources :users
  get 'register' => 'users#new', as: :register

  resources :articles
  resources :news

  resources :videos

  get 'login' => 'session#new', as: :login
  post 'login' => 'session#login'
  get 'logout' => 'session#logout', as: :logout

  root 'welcome#index'
  
end
