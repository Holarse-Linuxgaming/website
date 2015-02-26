#encoding :utf-8
class ArticlesController < ApplicationController

  before_filter :login_required, only: [:new, :create, :update, :destroy]

  add_breadcrumb "Artikel", :articles_path

  def index
    @articles = Article.all.decorate
    add_breadcrumb "Übersicht"
  end

  def show
    @article = Article.friendly.find(params[:id]).decorate
    add_breadcrumb @article.title, @article
  end

  def create
    @article = Article.create(article_params)

    if @article.save
      flash[:success] = "Der Artikel #{@article.title} wurde erfolgreich angelegt."
      redirect_to @article
    else
      flash[:error] = "Es gab ein Problem beim Speichern des Artikels"
      render :edit
    end
  end

  def edit
    @article = Article.friendly.find(params[:id])
    add_breadcrumb @article.title, @article
    add_breadcrumb "Bearbeiten"
  end

  def update
    @article = Article.friendly.find(params[:id])
    @article.update_attributes(article_params)

    if @article.save
      flash[:success] = "Der Artikel wurde aktualisiert."
      redirect_to @article
    else
      flash[:error] = "Es gab ein Problem beim Speichern des Artikels"
      render :edit
    end
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
