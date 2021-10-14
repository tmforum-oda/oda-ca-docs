docker build -t lesterthomas/productcatalogapi:0.2 -t lesterthomas/productcatalogapi:latest -f prodcat-dockerfile . 
docker push lesterthomas/productcatalogapi --all-tags
docker build -t lesterthomas/partyroleapi:0.2 -t lesterthomas/partyroleapi:latest -f partyrole-dockerfile . 
docker push lesterthomas/partyroleapi --all-tags
docker build -t lesterthomas/roleinitialization:0.3 -t lesterthomas/roleinitialization:latest -f roleinitialization-dockerfile . 
docker push lesterthomas/roleinitialization --all-tags
docker build -t lesterthomas/productcataloginitialization:0.1 -t lesterthomas/productcataloginitialization:latest -f productcataloginitialization-dockerfile . 
docker push lesterthomas/productcataloginitialization --all-tags

