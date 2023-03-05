provider "aws" {
  profile = "development-local"
  region = "us-east-1"
}

terraform {
  backend "s3" {}
}

module "vpc" {
  source = "./modules/vpc"
}

module "ec2" {
  source = "./modules/vpc"
  vpc_id = module.vpc.vpc_id
}