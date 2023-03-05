output "vpc_id" {
  value       = concat(aws_vpc.vpc.*.id, [""])[0]
}

output "vpc_arn" {
  value       = concat(aws_vpc.vpc.*.arn, [""])[0]
}

output "vpc_cidr_block" {
  value       = concat(aws_vpc.vpc.*.cidr_block, [""])[0]
}
