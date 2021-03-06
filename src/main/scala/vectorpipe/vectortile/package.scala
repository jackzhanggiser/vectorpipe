package vectorpipe

import geotrellis.vector._
import com.vividsolutions.jts.{geom => jts}

// --- //

package object vectortile {

  /** Ensure a [[geotrellis.vector.Polygon]] has the correct winding order
    * to be used in a [[VectorTile]].
    */
  def winding(p: Polygon): Polygon = {
    /* `normalize` works in-place, so we clone first to avoid clobbering the
     * GT Polygon.
     */
    val geom = p.jtsGeom.clone.asInstanceOf[jts.Polygon]
    geom.normalize

    /* `normalize` makes exteriors run clockwise and holes run
     * counter-clockwise, but assuming that (0,0) is in the bottom left. VTs assume
     * (0,0) is in the top-left, so we need to reverse the results of the
     * normalization.
     */
    Polygon(geom.reverse().asInstanceOf[jts.Polygon])
  }
}
