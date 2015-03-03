class WelcomeController < ApplicationController
  @layout = "welcome"

  def index
    @items = News.all.decorate
  end
end
