docker build -t lesterthomas/productcatalogapi:0.1 -t lesterthomas/productcatalogapi:latest -f prodcat-dockerfile . 
docker push lesterthomas/productcatalogapi --all-tags
docker build -t lesterthomas/partyroleapi:0.1 -t lesterthomas/partyroleapi:latest -f partyrole-dockerfile . 
docker push lesterthomas/partyroleapi --all-tags

