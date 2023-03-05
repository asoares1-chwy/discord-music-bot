output "ec2_id" {
  description = "The ID of the EC2"
  value       = concat(aws_instance.discord_music_bot.*.id, [""])[0]
}

output "ec2_arn" {
  description = "The ARN of the EC2"
  value       = concat(aws_instance.discord_music_bot.*.arn, [""])[0]
}
