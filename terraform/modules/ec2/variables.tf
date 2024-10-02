variable "ec2_name" {
  type    = string
  default = "discord-music-bot"
}

variable "ec2_security_group_name" {
  type    = string
  default = "discord-music-bot-sg"
}

variable "ec2_security_group_description" {
  type    = string
  default = "security group for discord music bot"
}

variable "ec2_ami" {
  description = "Amazon Linux 2023 AMI 2023.5.20240916.0 x86_64 HVM kernel-6.1"
  type        = string
  default     = "ami-0ebfd941bbafe70c6"
}

variable "ec2_instance_type" {
  type    = string
  default = "t3a.nano"
}

variable "vpc_id" {
  type = string
}

variable "ec2_ssh_key_name" {
  type    = string
  default = "discord-music-bot-ec2-key"
}

variable "public_subnet_id" {
  description = "The ID of the Public Subnet"
  type        = string
}