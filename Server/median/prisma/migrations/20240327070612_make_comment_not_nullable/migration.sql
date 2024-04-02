/*
  Warnings:

  - Made the column `Comment` on table `WorkProgress` required. This step will fail if there are existing NULL values in that column.

*/
-- AlterTable
ALTER TABLE "WorkProgress" ALTER COLUMN "Comment" SET NOT NULL;
