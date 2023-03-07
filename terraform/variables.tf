variable "ec2_ssh_public_key_path" {
  description = "The local path to the SSH Public Key"
  type        = string
  default     = "~/.ssh/id_rsa_dmb.pub"
}

variable "profile" {
  description = "AWS Profile"
  type        = string
  default     = "adriano-personal"
}

variable "region" {
  description = "Region for AWS resources"
  type        = string
  default     = "us-east-1"
}