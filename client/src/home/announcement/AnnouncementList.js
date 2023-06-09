import React from "react";
import AnnouncementCard from "./AnnouncementCard";

function AnnouncementList({announcements}) {
    return (
        <div className="row row-cols-3">
            {announcements.map((announcement) => (
                <AnnouncementCard key={announcement.id} announcement={announcement} />
            ))}
        </div>
    );
}

export default AnnouncementList;
