class SearchController < ApplicationController

  def index
    @results = PerformSearch.(params[:q]).decorate
  end

end
