Rails.application.routes.draw do
  
  resources :articles
  resources :news
  
  get 'search', to: "search#index"
  
  root 'welcome#index'
end
