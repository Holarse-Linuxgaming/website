class ArticlesController < ApplicationController

  before_filter :login_required, only: [:new, :create, :destroy]

  add_breadcrumb "Artikel"

  def index
    @articles = Article.all
  end

  def show
    @article = Article.friendly.find(params[:id]).decorate
    add_breadcrumb @article.title, @article
  end

  def create
    @article = Article.create(article_params)

    if @article.save
      flash[:notice] = "Der Artikel #{@article.title} wurde erfolgreich angelegt."
      redirect_to @article
    else
      flash[:error] = "Es gab ein PRoblem beim Speichern des Artikels"
      render :edit
    end
  end

  def edit
    @article = Article.find(params[:id])
  end

  def new
    add_breadcrumb "Neu"
    @article = Article.new
  end

  def destroy
  end

  private

  def article_params
    params.require(:article).permit(:title, :alternatives, :content)
  end

end
