Rails.application.routes.draw do

  get 'about' => 'static_page#about', as: :about

  resources :users
  get 'register' => 'users#new', as: :register

  get 'login' => 'session#new', as: :login
  post 'login' => 'session#login'
  get 'logout' => 'session#logout', as: :logout
 
  root 'welcome#index'
  
end
