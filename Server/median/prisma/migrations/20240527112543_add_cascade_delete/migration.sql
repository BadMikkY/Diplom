-- DropForeignKey
ALTER TABLE "WorkProgress" DROP CONSTRAINT "WorkProgress_BookingID_fkey";

-- AddForeignKey
ALTER TABLE "WorkProgress" ADD CONSTRAINT "WorkProgress_BookingID_fkey" FOREIGN KEY ("BookingID") REFERENCES "Booking"("BookingID") ON DELETE CASCADE ON UPDATE CASCADE;
