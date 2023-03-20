import { Switch } from "@nextui-org/react";


export default function AdminPanelCard({ user, switchRoles }) {

  return (
    <div className="p-3 border-bottom gold-members">
      <span className="float-right"> <Switch color="error" checked={user.userRole === "ADMIN" ? true : false} onChange={() => switchRoles(user.id)} /></span>
      <div className="media">
        <div className="mr-3 font-weight-bold text-danger non_veg">.</div>
        <div className="media-body">
          <h6 className="mb-1">{user.email}</h6>
          <p className="text-muted mb-0">{user.userRole}</p>
        </div>
      </div>
    </div>
  )
}