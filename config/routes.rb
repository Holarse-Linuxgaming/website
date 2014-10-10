Rails.application.routes.draw do
  
  get 'impressum', to: "static_page#imprint", as: :imprint

  get 'login', to: "session#login", as: :login
  get 'logout', to: "session#logout", as: :logout

  resources :articles
  resources :news
  resources :users

  get 'register', to: "user#new"
  get 'search', to: "search#index"
  
  root 'welcome#index'
end
