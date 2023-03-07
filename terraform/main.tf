provider "aws" {
  region = var.region
  profile = var.profile
}

#terraform {
#  backend "s3" {}
#}

module "vpc" {
  source = "./modules/vpc"
}

module "ec2" {
  source = "./modules/ec2"
  ec2_ssh_public_key_path = var.ec2_ssh_public_key_path
  vpc_id = module.vpc.vpc_id
}
